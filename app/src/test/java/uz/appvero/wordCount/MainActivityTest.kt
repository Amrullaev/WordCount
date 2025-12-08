package uz.appvero.wordCount

import org.junit.Test
import org.junit.Assert.*

class MainActivityTest {

    @Test
    fun countWordsInText_emptyString_returnsZero() {
        val text = ""
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 1: Empty string should return 0", 0, result)
    }

    @Test
    fun countWordsInText_singleWord_returnsOne() {
        val text = "Salom"
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 2: Single word 'Salom' should return 1", 1, result)
    }

    @Test
    fun countWordsInText_multipleWords_returnsCorrectCount() {
        val text = "Bu test uchun matn"
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 3: 'Bu test uchun matn' should return 4", 4, result)
    }

    @Test
    fun countWordsInText_multipleSpacesBetweenWords_returnsCorrectCount() {
        val text = "Ko'p    bo'shliqlar    bilan"
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 4: 'Ko\'p    bo\'shliqlar    bilan' should return 3", 3, result)
    }

    @Test
    fun countWordsInText_leadingAndTrailingSpaces_returnsCorrectCount() {
        val text = "  atrofida bo'shliqlar  "
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 5: '  atrofida bo\'shliqlar  ' should return 2", 2, result)
    }

    @Test
    fun countWordsInText_nullInput_returnsZero() {
        val text: String? = null
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 6: Null input should return 0", 0, result)
    }

    @Test
    fun countWordsInText_stringWithOnlySpaces_returnsZero() {
        val text = "   "
        val result = WordCounter.countWordsInText(text)
        assertEquals("Test 7: String with only spaces should return 0", 0, result)
    }
}