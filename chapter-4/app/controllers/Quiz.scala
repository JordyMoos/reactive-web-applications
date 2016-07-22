
package controllers

import javax.inject.Inject
import actors.QuizActor
import play.api.mvc.{ Action, Controller, WebSocket }
import services.VocabularyService
import play.api.i18n.Lang
import play.api.Play.current

class Quiz @Inject() (vocabularyService: VocabularyService) extends Controller {
  def quiz(sourceLang: Lang, targetLang: Lang) = Action {
    val vocabulary = vocabularyService.findRandomVocabulary(sourceLang, targetLang)
    vocabulary match {
      case Some(v) => Ok(v.word)
      case None => NotFound
    }
  }

  def check(sourceLang: Lang, word: String, targetLang: Lang, translation: String) = Action {
    val isCorrect = vocabularyService.verify(sourceLang, word, targetLang, translation)
    if (isCorrect) Ok else NotAcceptable
  }

  def quizEndPoint(sourceLang: Lang, targetLang: Lang) = {
    WebSocket.acceptWithActor[String, String] { request =>
      out => QuizActor.props(out, sourceLang, targetLang, vocabularyService)
    }
  }
}
