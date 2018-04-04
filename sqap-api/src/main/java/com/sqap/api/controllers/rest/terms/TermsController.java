package com.sqap.api.controllers.rest.terms;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rules")
public class TermsController {

    private final String creatorStatement = "1. Twórca testów oświadcza, że próbki dźwiękowe nie posiadają treści powszechnie uważanych za wulgarne i niezgodne z prawem.\n" +
            "2. Twórca testów oświadcza, że zaspokaja wszystkie licencje, prawa, zgody zamieszczaną treścią zarówno w chwili zamieszczenia, jak i podczas użytkowania portalu. Oświadcza też że nie łamie stosownych praw przechowując utwory w bazie danych portalu.\n" +
            "3. Twórca testów oświadcza, że ponosi wyłączną odpowiedzialność za własne treści oraz skutki zamieszczenia lub opublikowania.\n" +
            "4. Twórca testów oświadcza, że dane osobowe przetwarzał będzie wyłącznie w celach przeprowadzanego eksperymentu i nie będzie ich upubliczniał wraz z wynikami.\n" +
            "5. Twórca testów oświadcza, że udział w eksperymencie jest dobrowolny i nieodpłatny, a uczestnik\n" +
            "może w każdej chwili zrezygnować bez podania przyczyny. Uczestnik nie ponosi żadnych konsekwencji z tego powodu.\n" +
            "6. Twórca testów oświadcza, że jest to eksperyment poświęcony badaniu jakości dzwięku w zakresie zdefiniowanego przez Twórcę celu badań.\n";

    private final String testerStatement =
            "1. Słuchacz wyraża zgodę na uczestnictwo w eksperymencie poświęconym badaniu jakości dzwięku.\n" +
                    "2. Słuchacz zapoznał się z charakterystyką testów, akceptuje szacunkowy czas trwania testów i nie ma zastrzeżeń do celu badań.\n" +
                    "3. Słuchacz akceptuję fakt że po naciśnięciu na przycisk dowolnej próbki  dzwiękowej rozpocznie się odtwarzanie dzwięku.\n" +
                    "4. Słuchacz zapoznał się i akceptuje to że przed rozpoczęciem eksperymentu, a dalej każdego z testów powinien dostosować poziom odtwarzanego dzwięku poprzez maksymalne ściszenie urządzenia fizycznego. W chwili rozpoczęcia tj. po naciśnięciu na przycisk dowolnej próbki dzwiękowej, powinien dostosować poziom odtwarzanego dzwięku poprzez stopniowe zwiększanie głośności urządzenia do komfortowego dla siebie poziomu.\n" +
                    "5. Słuchacz akceptuje fakt że nie każda próbka musi posiadać zbliżony poziom głośności.\n" +
                    "6. Słuchacz nie może rościć praw z tytułu uszkodzenia słuchu ani do twórców portalu, twórców testów, ani twórców wykorzystywanych bibliotek.\n" +
                    "7. Słuchacz przyjmuje do wiadomości i akceptuje, że podczas korzystania z usług portalu, pomimo oświadczenia twórcy i nadzoru administracji może być narażony na treści obraźliwe, nieprzyzwoite lub które użytkownik uzna za niedopuszczalne. Słuchacz zrzeka się z tego tytułu praw do stosownych roszczeń wobec twórców portalu i twórcy testów. Słuchacz powinien zgłosić wszelkie niedopuszczalne treści administracji portalu.\n" +
                    "8. Słuchacz akceptuje to, że jego rezultaty wraz z danymi osobowymi tj. płeć, wiek, odpowiedz na pytanie osobiste - czy posiada Pan/Pani wadę/ubytek słuchu jak i login uczestnika (jeśli test nie jest przeprowadzany anonimowo) będą wysłane i przechowywane w bazie danych. Ze strony portalu rezultaty wraz z danymi osobowymi będą przetwarzane tylko i wyłącznie na potrzeby wyznaczenia odpowiednich statystyk, przesyłane będą dla twórcy danego zestawu testowego (zarówno w formie przetworzonej jak i w formie nieprzetworzonej, w różnym formacie plikowym). Dalsze przetwarzanie rezultatów wraz z danymi osobowymi reguluje Oświadczenie Twórcy.\n" +
                    "9. Słuchacz akceptuje to, że przed rozpoczęciem wykonywania testów z danej grupy testowej, z bazy danych serwisu pobrane będą na jego urządzenie pliki dzwiękowe niezbędne do przeprowadzenia danego testu. Dodatkowo w celu optymalizacji, podczas wykonywania testu pobierane będą niezbędne pliki dzwiękowe do przeprowadzenia testu następnego.\n" +
                    "10. Udział w eksperymencie jest dobrowolny i nieodpłatny, a słuchacz może w każdej chwili zrezygnować bez podania przyczyny. Uczestnik nie ponosi żadnych konsekwencji z tego powodu.\n";

    private final String registrationStatement = "Wyrażam zgodę na przetwarzanie i składowanie danych osobowych w celu umożliwienia mojego uwierzytelnienia i pracy w serwisie.\n";

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Statements> getStatements() {
        return new ResponseEntity<Statements>(new Statements(creatorStatement, testerStatement, registrationStatement), HttpStatus.OK);
    }
}

@Value
class Statements {
    String creatorStatement;
    String testerStatement;
    String registrationStatement;
}
