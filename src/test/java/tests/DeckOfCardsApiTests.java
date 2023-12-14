package tests;
import Logic.ApiCalls;
import Logic.ApiResponse;
import Logic.ApiResponseParser;
import infrastructure.WrapApiResponse;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/* IMPORTANT NOTE:
type: "npx allure-commandline serve" in terminal to open a website, viewing report results
*/

@ExtendWith(AllureJunit5.class)
public class DeckOfCardsApiTests
{
    private static String deckID;
    private static WrapApiResponse<ApiResponse> result = null;
    private static final String pileName="MaherPile";

    @BeforeAll
    public static void beforeAll() throws IOException {

        result = ApiCalls.createDeckWithJokers();
        result.setData(getJsonData(result.getData()));
        deckID=result.getData().getDeck_id();
    }
    //@Disabled
    @Test
    public void createDeckWithJokersTest() {
            // Assert
            assertTrue(result.getData().isSuccess());
            assertEquals(result.getStatus(), 200);
            assertEquals(result.getData().getRemaining(), 54);
    }

    //@Disabled
    @Test
    public void shuffleDeckCardsTest() {
        try {
            // Arrange
            result = ApiCalls.shuffleDeckCards(deckID, false);
            // Act
            result.setData(getJsonData(result.getData()));
            // Assert
            assertTrue(result.getData().isShuffled());
            assertTrue(result.getData().isSuccess());
            assertEquals(result.getStatus(), 200);
        } catch (IOException e) {
            // Handle or log the IOException
            e.printStackTrace();
            // You may choose to fail the test or take appropriate action based on your requirements
            fail("IOException occurred during test: " + e.getMessage());
        }
    }

    //@Disabled
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4 })
    public void drawCardsTest(int cardsToDraw) {
        try {
            // Arrange and act
            result = ApiCalls.drawCards(deckID, cardsToDraw);
            // Act
            result.setData(getJsonData(result.getData()));
            // Assert
            assertEquals(result.getData().getCards().size(), cardsToDraw);
            assertEquals(result.getStatus(), 200);
        } catch (IOException e) {
            // Handle or log the IOException
            e.printStackTrace();
            // You may choose to fail the test or take appropriate action based on your requirements
            fail("IOException occurred during test: " + e.getMessage());
        }
    }

    //@Disabled
    @Test
    public void createPileAddDrawnCardsTest() {
        try{
            // Arrange
             final String listOfDrawnCards = "1S,2S,3S,4S";
            result = ApiCalls.createPileAddDrawnCards(deckID, pileName, listOfDrawnCards);
            // Act
            result.setData(getJsonData(result.getData()));
            // Assert
            assertEquals(result.getStatus(), 200);
            assertNotNull(result.getData().getPiles(), "Pile is null.");
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace(); // Log or handle the exception according to your needs
            fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    //@Disabled
    @Test
    public void listCardsInPileTest() {
        try {
            // Arrange
            result = ApiCalls.listCardsinPile(deckID, pileName);
            // Act
            result.setData(getJsonData(result.getData()));
            // Assert
            assertEquals(result.getStatus(), 200);
            // Check for null to avoid NullPointerException
            Map<String, ApiResponse.Pile> pileList = result.getData().getPiles();
            for (Map.Entry<String, ApiResponse.Pile> entry : pileList.entrySet()) {
                ApiResponse.Pile pile = entry.getValue();
                assertEquals(pile.getRemaining(), pile.getCards().size());
                }
            }
         catch (IOException e) {
            // Handle or log the IOException
            e.printStackTrace();
            // You may choose to fail the test or take appropriate action based on your requirements
            fail("IOException occurred during test: " + e.getMessage());
        }
    }
    @AfterAll
    public static void destroyResultObject() {
        // Destroy or release resources associated with 'result' object
        result = null;
    }


    private static ApiResponse getJsonData(Object data){
        String jsonData=String.valueOf(data);
        return ApiResponseParser.parseJson(String.valueOf(jsonData));
    }

}
