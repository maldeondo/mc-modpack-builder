/*
*  Copyright 2026 Mario Aldeondo (@maldeondo)
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/

package mc.modpack.builder.misc;

import org.junit.jupiter.api.Test;

import mc.modpack.builder.Utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

class APIKeyTest {
    private static String path = Utils.WORKING_DIR + "CURSEFORGE_API_KEY";
    private static String previousKey = null;
    
    @BeforeAll static void storePreviousKey() {
        try {
            previousKey = Files.readString(Path.of(path));
            Files.delete(Path.of(path));  
        } 
        catch (NoSuchFileException ex) {
            System.out.println("Previous key not found.");
        } 
        catch (IOException ex) {
            System.out.println("Unknown error.");
        }
    }
    
    @Test void checkValidKey() {
        try {
            Files.writeString(Path.of(path), Utils.encodeB64("this_is_a_secret_key"), StandardOpenOption.CREATE_NEW);

            assertEquals("this_is_a_secret_key", APIKey.fetchKey(), "Must return the decoded value from disk.");
        }
        catch (IOException ex) {
            System.out.println("Unknown error.");
        }
    }

    @Test void checkEnv() {
        try {
            assertEquals(Utils.decodeB64(System.getenv("CURSEFORGE_API_KEY")), APIKey.fetchKey());        
        }
        catch (IOException ex) {
            System.out.println("Unknown error.");
        }
    }

    @AfterEach void deleteTestFile() {
        try {
            Files.deleteIfExists(Path.of(path));
        } 
        catch (IOException ex) {
            System.out.println("Unknown error.");
        }
    }

    @AfterAll static void restorePreviousKey() {
        if (Utils.validString(previousKey)) {
            try {
                Files.writeString(Path.of(path), previousKey, StandardOpenOption.CREATE);
            } 
            catch (IOException ex) {
                System.out.println("Unknown error.");
            }
        }
    }
}
