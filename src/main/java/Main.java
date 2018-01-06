import library.WordSquare;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            /* grab inputs */
            String arg0, arg1;
            try {
                arg0 = args[0];
                arg1 = args[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("Arguments required: Integer, String");
            }

            /* convert first parameter to an integer */
            int n;
            try {
                n = Integer.parseInt(arg0);
            } catch (NumberFormatException e) {
                throw new Exception("First argument must be an Integer");
            }

            /* test use */
            if ("all".equals(arg1)) {
                arg1 = "abcdefghijklmnopqrstuvwxyz";
            }

            /* run wordsquare */
            WordSquare wordSquare = new WordSquare(n, arg1);

            if (args.length == 3) {
                /* find all results */
                List<List<String>> results = wordSquare.findAll();
                System.out.println(results.size() + " results found");
                for (List<String> res : results) {
                    System.out.println(res);
                }
            } else {
                /* find first result */
                List<String> result = wordSquare.findOne();
                System.out.println(result);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
