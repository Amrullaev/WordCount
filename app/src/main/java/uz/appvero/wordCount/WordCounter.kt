package uz.appvero.wordCount

object WordCounter {
    fun countWordsInText(text: String?): Int {
        if (text.isNullOrBlank()) {
            return 0
        }
        val words = text.trim().split("""\s+""".toRegex()).filter { it.isNotEmpty() }
        return words.size
    }
}