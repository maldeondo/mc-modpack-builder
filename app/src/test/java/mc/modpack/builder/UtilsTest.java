/*
*  Copyright 2026 Mario Aldeondo
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

package mc.modpack.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UtilsTest {
    @Test void modTypeFormatCorrect() {
        // Check wether String-equivalent mod type is correct
        assertEquals("C", Utils.modTypeFormat(0), "Type 0 means C (Client");
        assertEquals("S", Utils.modTypeFormat(1), "Type 1 means S (Server");
        assertEquals("C+S", Utils.modTypeFormat(2), "Type 2 means C+S (Client+Server)");
        assertEquals("", Utils.modTypeFormat(3), "Other types mean nothing (void String)");
    }

    @Test void modStatusFormatCorrect() {
        // Check wether String-equivalent mod status is correct
        assertEquals("-", Utils.modStatusFormat(0), "Type 0 means - (Non-Active)");
        assertEquals("A", Utils.modStatusFormat(1), "Type 1 means A (Active)");
        assertEquals("A+P", Utils.modStatusFormat(2), "Type 2 means A+P (Active+Production)");
        assertEquals("C", Utils.modStatusFormat(3), "Type 3 means C (Cancelled)");
        assertEquals("", Utils.modTypeFormat(4), "Other types mean nothing (void String)");
    }

    @Test void repeatIsCorrect() {
        // Check wether repeat returns the correct String
        assertEquals("-------", Utils.repeat(7, "-"), "Returns 7 consecutive - chars");
        assertEquals("HeaderHeaderHeader", Utils.repeat(3, "Header"), "Returns 3 consecutive Header Strings");
        assertEquals("", Utils.repeat(0, "anything"), "Anything repeated 0 times equals a void String");
    }

    @Test void validStringIsCorrect() {
        // Check wether validString recognizes non-valid Strings
        assertTrue(Utils.validString("valid"), "Valid String returns true");
        assertFalse(Utils.validString(""), "Void String returns false");
        assertFalse(Utils.validString(null), "Null returns false");
    }

    @Test void validIndexIsCorrect() {
        // Check wether validIndex recognizes out-of-bounds integers
        assertTrue(Utils.validIndex(2, 4), "Valid standard index");
        assertTrue(Utils.validIndex(2, 2), "Max index returns true");
        assertTrue(Utils.validIndex(0, 0), "Zero (as number) always returns true");
        assertFalse(Utils.validIndex(2, 1), "Higher index than valid returns false");
        assertFalse(Utils.validIndex(-1, 2), "Negative integers always returns false");
    }
}
