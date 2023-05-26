package my.project.testretrofit.utils

class Validator {

    fun validatePassword(password: String): Boolean {
        val pattern = "^[a-zA-Z0-9]{5,20}\$".toRegex()
        return pattern.matches(password)
    }

    fun validateUsername(username: String): Boolean {
        val pattern = "^[a-zA-Z][a-zA-Z0-9]{4,11}$".toRegex()
        return pattern.matches(username)
    }

    fun validateNumber(number: String): Boolean {
        val pattern = "^((\\+7|7|8)+([\\- ]?))+(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}\$".toRegex()
        return pattern.matches(number)
    }

    fun validateEmail(email: String): Boolean {
        val pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}\$".toRegex()
        return pattern.matches(email)
    }

    fun getPasswordRequirements(): String {
        return "The password must be at least one letter (uppercase or lowercase), " +
                "as a maximum of one digit, consist of a letter and a number, " +
                "and must be at least 8 to 50 characters long."
    }

    fun getUsernameRequirements(): String {
        return "The username must consist of Latin letters (uppercase or lowercase), " +
                "numbers, underscores (_) and hyphens (-), " +
                "and its length must be from 3 to 25 characters."
    }

    fun getEmailRequirements(): String {
        return "Email must begin with a Latin letter and contain characters before and after " +
                "the @ symbol and the symbol . (dot)."
    }

    fun getNumberRequirements(): String {
        return "Number not valid"
    }


}