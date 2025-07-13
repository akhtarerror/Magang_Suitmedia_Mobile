package mber.suitmedia.myapplication.utils

object PalindromeChecker {
    fun isPalindrome(text: String): Boolean {
        val cleaned = text.replace(Regex("[^A-Za-z0-9]"), "").lowercase()
        return cleaned == cleaned.reversed()
    }
}