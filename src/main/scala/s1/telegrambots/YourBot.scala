package s1.telegrambots
import s1.telegrambots.BasicBot

import scala.collection.mutable
import scala.collection.mutable.Buffer
import scala.util.Using
import scala.io.Source
import scala.io.StdIn.readLine
import scala.collection.mutable.Map
import scala.util.Random

object YourBot extends App:
    object Bot extends BasicBot:

       var language = ""

        this.run()
        println("Started the bot")

        var sanaLista = scala.collection.mutable.Map[String, String]()
        def help(msg: Message) =
            "/start - When you want start learning!\n"+
            "/addLanguage -  When adding the language\n"+
            "/add - When adding words\n"+
            "/quiz - When you want to start the quiz!\n"+
            "/answer - When answering!"

        def alku(msg: Message) = help(msg)+"\n\nWhat language do you want to learn?"

        def mikäKieli(msg: Message) =
          language = getString(msg)
          s"Language chosen. Give me the words in format: /add English, $language"

        def sanaTallennus(msg: Message) =
          val sanat = getString(msg)
          val sanaparit = sanat.split(",")
          if  sanaparit.size != 2 then
            s"Wrong format. Please use the following format: /add English, $language"
          else
            sanaLista.addOne(sanaparit(0).trim, sanaparit(1).trim)
            Random.shuffle(sanaLista)
            "Added"

        var eka = ("", "")

        def quiz(msg: Message) =
          if sanaLista.isEmpty then
            s"No more words to quiz. Use /add command."
          else
            eka = sanaLista.head
            s"What is ${eka.head} in $language?"

        def answer(msg:Message) =
          sanaLista -= eka.head
          val answer = eka.last
          if getString(msg) == answer then
            ("Correct!")
          else
            sanaLista.addOne(eka)
            ("Try again!")

        this.onUserCommand("start", alku)
        this.onUserCommand("addLanguage", mikäKieli)
        this.onUserCommand("add", sanaTallennus)
        this.onUserCommand("quiz", quiz)
        this.onUserCommand("help", help)
        this.onUserCommand("answer", answer)

    end Bot

    val bot = Bot 
end YourBot
