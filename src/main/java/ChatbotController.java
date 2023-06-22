import org.springframework.ui.Model;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController


@Controller
public class ChatbotController {

    @Autowired
    private StanfordCoreNLP coreNLP;
    @RequestMapping("/")
    public String chatbotView() {

        return "chatbot"; // Return the name of your HTML template
    }

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public String analyzeInput(@RequestParam("input") String userInput, Model model) {
        // Perform sentiment analysis using CoreNLP
        Annotation document = new Annotation(userInput);
        coreNLP.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        String sentiment = sentences.get(0).get(SentimentCoreAnnotations.SentimentClass.class);
        String response;

        //Generating responses to user
        if(userInput.toLowerCase().contains("hi"+ "Hello" + "Hey" + "hey" + "hello" + "Hi" )){
            response = "Hello ! How can I help you ?";
        }
        else{
            response = "I did not understand";
        }


        // Add the sentiment analysis result to the model
        model.addAttribute(sentiment,"sentiment");
        model.addAttribute(response,"response");

        return "chatbot";
    }
}
