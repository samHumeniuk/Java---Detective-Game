import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Person - implements characters which can be interacted with.
 *
 *
 * "Person" represents one character in the game.  They
 * can be spoken to.  Different questions can be asked and
 * different responses will be made by the person.
 * 
 * @author  UP688841 and UP695745
 * @version 
 */

public class Person
{
    private String name;
    private String description;
    private ArrayList<String> questions;
    private ArrayList<String> answers;

    /**
     * Create a character that can be spoken to.
     * 
     */
    public Person(String name, String description)
    {
        this.name = name;
        this.description = description;
        questions = new ArrayList<String>();
        answers = new ArrayList<String>();
    }
    
    /**
     * @return the characters name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * @return the description of the character.
     */
    public String getDescription() {
        return description;
    }
        
    /**
     * @return An arraylist that contains the questions that the character can be asked.
     */
    public ArrayList getQuestions() {
        return questions;
    }
    
    /**
     * adds a question and a corresponding answer to the list of questions that can be asked.
     * @param question - sets the question that is asked.
     * @param answer - sets the responce to the question.
     */
    public void SetConversation(String question, String answer) {
        questions.add(question);
        answers.add(answer);
    }
    
     /**
     * @return The number of questions with responces that the character has.
     */
    public int getNumberOfQuestions() {
        return questions.size();
    }
    
     /**
     * @param An int that respresents a certin question.
     * @return The question that correponds with the question number.
     */
        public String getQuestion(int questionNumber) {
        String question;
        question = questions.get(questionNumber -1);
        return question;
    }
    
    /**
     * @param An int that respresents a certin question.
     * @return The question that correponds with the question number.
     */
    public String askQuestion(int questionNumber) {
        String answer;
        answer = answers.get(questionNumber -1);
        return answer;
    }
    
}








