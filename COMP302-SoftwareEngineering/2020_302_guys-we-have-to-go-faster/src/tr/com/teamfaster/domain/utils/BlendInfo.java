package tr.com.teamfaster.domain.utils;

public class BlendInfo {
    private final int input;
    private final int output;

    public BlendInfo(int input, int output) {
        this.input = input;
        this.output = output;
    }

    public int getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }

}
