

 
 
 
 
 



















class MyClass {

    private static void printer( boolean flag ) {
        if ( flag )
            System.out.println( "Correct!" );
        else
            System.out.println( "Incorrect!" );
    }

    
         public static void func( int i ) {  printer( i ==     1 ); }
    

    public static void main(String[] args) {

        String x;

        if (               true )
            printer( true );

        x = new String("Correct!");
        printer( x.equals( "Correct!" ) );

        
        printer( 2 + 2 == 4 );

        x = new String( "Correct!\n" + "Correct!\n" );
        printer(      !   x.equals( "Correct!" ) );

        
        x = new String(   "fine" +  "fine" );
        printer( x.equals( "finefine" ) );

        func(    1);

        
        char [] tester = new char[] { 'C', 'O', 'R', 'R', 'E', 'C', 'T' };
        String comp = new String( tester );
        
        x = new String( "define what is CORRECT and what is just OK" );
        printer(x.indexOf(comp) == 15);
    }

}
