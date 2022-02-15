public class HeuristicFunction {

    @FunctionalInterface
    public interface heuristicFunction{
        int getHeuristics(Coordinate cur, Coordinate g, Direction d);
    }

    public static heuristicFunction NoHeuristics = HeuristicFunction::noHeuristics;
    public static heuristicFunction MinOfVerticalAndHorizontal = HeuristicFunction::minOfVerticalAndHorizontal;
    public static heuristicFunction MaxOfVerticalAndHorizontal = HeuristicFunction::maxOfVerticalAndHorizontal;

    public static heuristicFunction SumOfVerticalAndHorizontal = HeuristicFunction::sumOfVerticalAndHorizontal;
    public static heuristicFunction CustomHeuristic = HeuristicFunction::customHeuristic;
    public static heuristicFunction HeuristicSix = HeuristicFunction::heuristicSix;

    static int noHeuristics(Coordinate cur, Coordinate g, Direction d){
        return 0;
    }

    static int minOfVerticalAndHorizontal(Coordinate cur, Coordinate g, Direction d){
        return Math.min(Math.abs(cur.getI() - g.i), Math.abs(cur.getJ() - g.j));
    }

    static int maxOfVerticalAndHorizontal(Coordinate cur, Coordinate g, Direction d){
        return Math.max(Math.abs(cur.getI() - g.i), Math.abs(cur.getJ() - g.j));
    }

    static int sumOfVerticalAndHorizontal(Coordinate cur, Coordinate g, Direction d){
        return Math.abs(cur.getI() - g.i) + Math.abs(cur.getJ() - g.j);
    }

    static int customHeuristic(Coordinate cur, Coordinate g, Direction d){
        int horzAndVert = sumOfVerticalAndHorizontal(cur, g, d);
        if(requiresTwoTurns(cur, g, d))
            return horzAndVert + 2;
        else if (cur.getI() == g.getI() || cur.getJ() == g.getJ())
            return horzAndVert;
        else
            return horzAndVert + 1;
    }

    private static boolean requiresTwoTurns(Coordinate cur, Coordinate g, Direction d) {
        switch (d) {
            case N:
                return cur.getI() < g.getI();
            case S:
                return cur.getI() > g.getI();
            case E:
                return cur.getJ() > g.getJ();
            case W:
                return cur.getJ() < g.getJ();
            default:
                return false;
        }
    }

    static int heuristicSix(Coordinate cur, Coordinate g, Direction d){
        return customHeuristic(cur, g, d) * 3;
    }



}
