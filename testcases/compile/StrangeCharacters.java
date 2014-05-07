
class StrangeCharacters {
  public static void main(String[] args) {
    /* Since Tigris already tests for codepoints outside of ASCII, might as
     * well also write a test case that tests for outside-of-BMP codepoints.
     * :-)  Here comes a cow face: 🐮
     */

    // Oh yeah, and line feeds constitute valid whitespace in Java...
    // I guess it's reasonable to limit whitespace to {CR,LF,Tab,Space} for
    // Minijava, but the grammar "spec" doesn't specify anything... so it's
    // reasonable to assume that Java dictates what is valid here.
    inta;

    // Also, might as well test for excessive length in identifiers...
    int aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa;
  }
}
