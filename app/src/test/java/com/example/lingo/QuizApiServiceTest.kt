import com.example.lingo.data.QuizScore
import com.example.lingo.network.QuizApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: QuizApiService

    @Before
    fun setup() {
        // Creating new web server
        mockWebServer = MockWebServer()
        // Starting server
        mockWebServer.start()

        // Retrofit instance that points to mock server instead of real one
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // important!
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }

    @After
    fun teardown() {
        // Shutting down server after testing
        mockWebServer.shutdown()
    }

    @Test
    fun SubmitScore_ReturnSuccess() = runBlocking {
        // Preparing fake response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"
                |deviceId":"abc123",
                |"language":"english",
                |"quizName":"Test Quiz",
                |"score":4}""".trimMargin())
        mockWebServer.enqueue(mockResponse)

        // Test score to be submitted
        val score = QuizScore("abc123", "english", "Test Quiz", 4)
        // Submitting score
        val response = apiService.submitScore(score)

        // Asserting that response is successful
        assert(response.isSuccessful)
        // Asserting that returned score is 4
        assert(response.body()?.score == 4)
    }

    @Test
    fun GetScore_ReturnScore() = runBlocking {
        // Preparing fake response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{
                |"deviceId":"abc123",
                |"language":"english",
                |"quizName":"Test Quiz",
                |"score":4
                |}""".trimMargin())
        mockWebServer.enqueue(mockResponse)

        // Getting response given quiz score properties
        val response = apiService.getScore("abc123", "english", "Test Quiz")

        // Asserting that response is successful
        assert(response.isSuccessful)

        // Asserting that response matches getScore call
        val body = response.body()
        assert(body != null)
        assert(body!!.deviceId == "abc123")
        assert(body.language == "english")
        assert(body.quizName == "Test Quiz")
        assert(body.score == 4)
    }
}