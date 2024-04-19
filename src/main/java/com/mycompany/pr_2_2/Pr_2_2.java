/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.pr_2_2;

/**
 *
 * @author andre
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Pr_2_2 {

    public static void main(String[] args) {
        System.out.println("Практическая работа 2_2, Кравцов Андрей, РИБО-02-22");
        String server = "https://android-for-students.ru";
        String serverPath = "/materials/practical/hello.php";
        HashMap<String, String> map = new HashMap();
        map.put("name", "Kravtsov Andrey");
        map.put("group", "RIBO-02-22");
        HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath, map);
        Thread th = new Thread(httpRunnable);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            JSONObject jsonObject = new JSONObject(httpRunnable.getResponseBody());
            int result = jsonObject.getInt("result_code");
            System.out.println("Result: " + result);
            System.out.println("Type: " + jsonObject.getString("message_type"));
            try {
                System.out.println("Text: " + jsonObject.getString("message_text"));
            } catch (JSONException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            switch (result) {
                case 1:
                    JSONArray jsonArray = jsonObject.getJSONArray("task_list");
                    System.out.println("Task list:");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        System.out.println((i + 1) + ") " + jsonArray.get(i));
                    }
                    break;
                case 0:
                    System.out.println("Result code is 0, please, try again");
            }
        }
    }
}