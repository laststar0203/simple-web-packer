package com.hm.packer.application.recipe;

import com.hm.packer.model.dto.Recipe;
import com.hm.packer.model.entity.Package;
import com.hm.packer.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@DependsOn("packageService")
public class RecipeConfig {


    @Autowired
    private PackageService packageService;

    @Value("${installListFilePath}")
    private String installListFilePath;

    @Bean
    public RecipeProvider recipeProvider() throws FileNotFoundException {
        File installList = new File(installListFilePath);
        JsonUserInstallListParser parser = new JsonUserInstallListParser(installList);

        Package[] data = parser.getAPackages();
        Recipe[] recipes = parser.getRecipes();


        //DB에 정보가 없다면 처음 실행되었다는 것이고 설정값.json 을 파싱한 후 DB에 새롭게 집어넣는다.
        if(packageService.isFirst(data.length)){
            packageService.init(data);
        }else { //반대로 Recipe의 installed 값을 바꿔준다.
            for (int index : Arrays.stream(packageService.getClearInstallFiles()).map(file -> file.getNumber()).collect(Collectors.toList())) {
                recipes[index].setInstalled(true);
            }
        }

       return new RecipeProvider(recipes);
    }

}
