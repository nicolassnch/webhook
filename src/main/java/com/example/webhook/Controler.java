package com.example.webhook;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controler {

    @PostMapping("/webhook")
    public String handleWebhook(@RequestBody String payload) throws IOException, InterruptedException {

        String projectRepoPath = "/home/ec2-user/spring/back-end-Spring";

        Process gitPullProcess = Runtime.getRuntime().exec("git -C " + projectRepoPath + " pull");
        gitPullProcess.waitFor();

        // Recharger le projet Maven
        Process mavenProcess = Runtime.getRuntime().exec("mvn -f " + projectRepoPath + " clean install");
        mavenProcess.waitFor();

        // Lancer le JAR
        String jarPath = projectRepoPath + "/target/Back-End-0.0.1.jar"; // Chemin complet vers votre fichier JAR
        Process jarProcess = Runtime.getRuntime().exec("java -jar " + jarPath);
        jarProcess.waitFor();

        return "Déploiement terminé";
    }

}
