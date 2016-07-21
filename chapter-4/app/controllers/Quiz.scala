
package controllers

import javax.inject.Inject

import play.api.mvc.{ Action, Controller }
import services.VocabularyService
import play.api.i18n.Lang

class Quiz @Inject() (vocabularyService: VocabularyService) extends Controller {
  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = Action {
    val vocabulary = vocabularyService.findRandomVocabulary(sourceLanguage, targetLanguage)
    vocabulary match {
      case Some(v) => Ok(v.word)
      case None => NotFound
    }
  }

  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = Action {
    val isCorrect = vocabularyService.verify(sourceLanguage, word, targetLanguage, translation)
    if (isCorrect) Ok else NotAcceptable
  }
}
