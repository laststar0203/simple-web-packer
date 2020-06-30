package com.hm.packer.application.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.hm.packer.model.dto.Recipe;
import com.hm.packer.model.entity.Package;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUserInstallListParser {

    private File installListJsonFile;
    private JsonElement jsonData;

    @Getter
    private Package[] aPackages;
    @Getter
    private Recipe[] recipes;


    public JsonUserInstallListParser(File installListJsonFile) throws FileNotFoundException {
        this.installListJsonFile = installListJsonFile;
        this.jsonData = JsonParser.parseReader(new JsonReader(new FileReader(installListJsonFile)));

        parse();
    }

    private void parse(){

        JsonArray list = jsonData.getAsJsonObject().getAsJsonArray("list");
        this.aPackages = new Package[list.size()];
        this.recipes = new Recipe[list.size()];


        for(int i = 0; i < list.size(); i++){

            JsonObject element = list.get(i).getAsJsonObject();
            int number = element.get("number").getAsInt();
            String fileName = element.get("fileName").getAsString();
            String fileVersion = element.get("fileVersion").getAsString();
            String stroagePath = element.get("storagePath").getAsString();

            this.aPackages[i] = new Package(number, fileName, fileVersion, 0);
            this.recipes[i] = new Recipe(number, fileName, fileVersion, stroagePath);

            JsonObject install = element.get("install").getAsJsonObject();
            this.recipes[i].setInstallScript(install.get("script").isJsonNull() ? null : install.get("script").getAsString());

            List<String> installProperties = new ArrayList<>();
            for(JsonElement e : install.get("properties").getAsJsonArray()){
                installProperties.add(e.getAsJsonObject().get("name").getAsString());
            }
            this.recipes[i].setInstallProperties(installProperties);

            JsonObject _do = element.get("do").getAsJsonObject();
            List<String> doScripts = new ArrayList<>();
            for (JsonElement e : _do.getAsJsonArray("scripts")) {
                doScripts.add(e.getAsString());
            }
            this.recipes[i].setDoScripts(doScripts);


            JsonObject check = element.get("check").getAsJsonObject();
            this.recipes[i].setCheckScript(check.get("script").isJsonNull() ? null : check.get("script").getAsString());
            JsonObject _assert = check.get("assert").getAsJsonObject();

            this.recipes[i].setAssertValue(_assert.get("value").isJsonNull() ? null : _assert.get("value").getAsString());
            this.recipes[i].setAssertPos( _assert.get("pos").isJsonNull() ? null : _assert.get("pos").getAsString());

            this.recipes[i].setDeleteScript(element.get("delete").getAsJsonObject().get("script").isJsonNull() ? null : element.get("delete").getAsJsonObject().get("script").getAsString());
        }
    }

    private List<String> recipeMakePropertyList(JsonArray array){
        if(array == null)
            return null;

        List<String> properties = new ArrayList<>(array.size());
        for (JsonElement e : array){
            properties.add(e.getAsJsonObject().get("name").getAsString());
        }
        return properties;
    }


}
