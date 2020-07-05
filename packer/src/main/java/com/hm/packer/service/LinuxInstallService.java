package com.hm.packer.service;

import com.hm.packer.application.command.LinuxCommandExecutor;
import com.hm.packer.application.exception.InstallAlreadyInstalledFileException;
import com.hm.packer.model.dto.Recipe;
import com.hm.packer.application.recipe.RecipeProvider;
import com.hm.packer.model.entity.Package;
import com.hm.packer.model.mapper.PacakgeMapper;
import com.hm.packer.model.network.ResponseRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinuxInstallService {

    @Lazy
    @Autowired
    private RecipeProvider recipeProvider;

    @Autowired
    private LinuxCommandExecutor commandExecutor;

    @Autowired
    private PacakgeMapper mapper;

    @Transactional
    public void install(int number, Map<String, String> param) throws Exception {
         Recipe recipe = recipeProvider.get(number);


         if(recipe.isInstalled())
             throw new InstallAlreadyInstalledFileException("This file is already installed.");
         //install

        //Param 사이즈가 맞지않음

         if(recipe.getInstallProperties().size() != param.size())
            throw new Exception("Invalid number of property values.");

         String[] params = new String[recipe.getInstallProperties().size()];
         for(int i = 0; i < params.length; i++){
             params[i] = (String)param.get(recipe.getInstallProperties().get(i));
         }

         List<String> result = new ArrayList<>();

        commandExecutor.command(recipe.getInstallScript(), params, result);

        System.out.println(result);

        result.clear();

        //do
        for(String command : recipe.getDoScripts()){
            commandExecutor.command(command, result);
            System.out.println(result);
            result.clear();
        }

        //check
        List<String> checkResult = new ArrayList<>();
        commandExecutor.command(recipe.getCheckScript(), checkResult);

        if(!checkResult.get(0).equals(recipe.getAssertValue()))
            throw new Exception("install Failed");

        mapper.updateInstalled(number, 1);
        recipe.setInstalled(true);

    }

    public List<Package> readInstallFileList() {
        return Arrays.stream(recipeProvider.getRecipes())
                .map(recipe -> new Package(recipe.getNumber(), recipe.getFileName(), recipe.getFileVersion(), recipe.isInstalled() ? 1 : 0))
                .collect(Collectors.toList());
    }

    public ResponseRecipe readInstallFile(int number) throws Exception {
        Recipe recipe = recipeProvider.get(number);
        return ResponseRecipe.builder()
                .number(recipe.getNumber())
                .fileName(recipe.getFileName())
                .fileVersion(recipe.getFileVersion())
                .installed(recipe.isInstalled())
                .installProperties(recipe.getInstallProperties())
                .build();
    }
}
