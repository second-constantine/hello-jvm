package by.next.way.cucumber.kotlin

object IsItFriday {
  fun isItFriday(today: String?): String {
    return if ("Friday" == today) "TGIF" else "Nope"
  }
}