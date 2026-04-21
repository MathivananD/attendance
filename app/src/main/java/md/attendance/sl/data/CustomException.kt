package md.attendance.sl.data

sealed class CustomException(var msg: String) : Exception(msg) {
    class NoNetWorkException : CustomException("Internet not available")

}