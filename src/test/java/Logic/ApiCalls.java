package Logic;
import java.io.IOException;
import java.util.HashMap;
import infrastructure.HttpFacade;
import infrastructure.HttpMethod;
import infrastructure.WrapApiResponse;
public class ApiCalls {
    private static final String BASE_URL = "https://deckofcardsapi.com/api/deck/";

    public static WrapApiResponse createDeckWithJokers() throws IOException {
        String url = BASE_URL + "new/?jokers_enabled=true";
        return HttpFacade.sendHttpRequest(url, HttpMethod.GET, new HashMap<>(), null,null);
    }
    public static WrapApiResponse shuffleDeckCards(String deckId, boolean remaining) throws IOException {
        String url = BASE_URL + deckId+ "/shuffle/";
        if (remaining) {
            url += "?remaining=true";
        }
        return HttpFacade.sendHttpRequest(url, HttpMethod.GET, new HashMap<>(), null,null);
    }

    public static WrapApiResponse drawCards(String deck_id, int cardsToDraw) throws IOException {
        String url = BASE_URL + deck_id + "/draw/?count="+cardsToDraw;
        return HttpFacade.sendHttpRequest(url, HttpMethod.GET, new HashMap<>(), null,null);
    }
    public static WrapApiResponse createPileAddDrawnCards(String deck_id, String pile_name, String listOfCards)throws IOException{
        String url = BASE_URL + deck_id + "/pile/"+ pile_name+ "/add/"+"?cards="+listOfCards;
        return HttpFacade.sendHttpRequest(url, HttpMethod.GET, new HashMap<>(), null,null);
    }
    public static WrapApiResponse listCardsinPile(String deck_id, String pile_name)throws IOException {
        String url = BASE_URL + deck_id + "/pile/" + pile_name + "/list/";
        return HttpFacade.sendHttpRequest(url, HttpMethod.GET, new HashMap<>(), null,null);
    }
}
