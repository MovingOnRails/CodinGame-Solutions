import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static List<String> permutationsRecursive(String prefix, String LETTERS){
        List<String> res = new LinkedList<>();
        int lettersLength = LETTERS.length();
        if(lettersLength == 0){
            res.add(prefix);
            return res;
        }
        for(int i=0;i<lettersLength;i++){
            List<String> permutationsRecursiveCase  = permutationsRecursive(prefix+LETTERS.charAt(i), 
            LETTERS.substring(0,i)+LETTERS.substring(i+1,lettersLength));
            res.addAll(permutationsRecursiveCase);
        }
        return res;
    }

    public static List<String> permutations(String LETTERS){
        return permutationsRecursive("",LETTERS);
    }

    public static List<String> everyPossibleWord(String LETTERS){
        List<String> res = new LinkedList<>();
        List<String> permutations = permutations(LETTERS);
        res.add(LETTERS);
        res.addAll(permutations);
        for(int i=0;i<LETTERS.length();i++){
            StringBuilder sb = new StringBuilder(LETTERS);
            sb.deleteCharAt(i);
            String LETTERSMinusOne = sb.toString();
            List<String> recursiveCase = everyPossibleWord(LETTERSMinusOne);
            res.addAll(recursiveCase);
        }
        return res;
    }

    public static int getScore(String word){
        int score = 0;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            switch(c){
                case 'e':
                case 'a':
                case 'i':
                case 'o':
                case 'n':
                case 'r':
                case 't':
                case 'l':
                case 's':
                case 'u':
                    score++;
                    break;
                case 'd':
                case 'g':
                    score += 2;
                    break;
                case 'b':
                case 'c':
                case 'm':
                case 'p':
                    score += 3;
                    break;
                case 'f':
                case 'h':
                case 'v':
                case 'w':
                case 'y':
                    score += 4;
                    break;
                case 'k':
                    score += 5;
                    break;
                case 'j':
                case 'x':
                    score += 8;
                    break;
                case 'q':
                case 'z':
                    score += 10;
                    break;
            }
        }
        return score;
    }

    public static Map<String, Integer> getScoresForWords(List<String> validWords){
        Map<String, Integer> scoreMap = new HashMap<>();
        for(String validWord : validWords){
            int score = getScore(validWord);
            scoreMap.put(validWord, score);
        }
        return scoreMap;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        Set<String> dictionary = new HashSet<>();
        List<String> dictionaryList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            String W = in.nextLine();
            dictionary.add(W);
            dictionaryList.add(W);
        }
        String LETTERS = in.nextLine();
        List<String> possibleWords = everyPossibleWord(LETTERS);
        List<String> validWords = new ArrayList<>();
        for(String p : possibleWords){
            if(dictionary.contains(p)){
                validWords.add(p);
            }
        }
        Map<String, Integer> wordsScore = getScoresForWords(validWords);
        String maxScoreWord = "invalidWord";
        int maxScoreWordScore = 0;
        for(String word : validWords){
            if(maxScoreWordScore < wordsScore.get(word)){
                maxScoreWord = word;
                maxScoreWordScore = wordsScore.get(word);
            }
        }
        List<String> maxScoreWords = new LinkedList<>();
        for(String word : validWords){
            if(getScore(word) == maxScoreWordScore){
                maxScoreWords.add(word);
            }
        }
        boolean foundWord = false;
        for(String dictionaryWord : dictionaryList){
            for(String maxScoreW : maxScoreWords){
                if(maxScoreW.equals(dictionaryWord)){
                    maxScoreWord = dictionaryWord;
                    foundWord = true;
                    break;
                }
            }
            if(foundWord){
                break;
            }
        }
        
        
        System.err.println("validWords: ");
        System.err.println(validWords);
        System.err.println("wordsScore: ");
        System.err.println(wordsScore);
        System.err.println("maxScoreWord: ");
        System.out.println(maxScoreWord);
    }
}
