public interface ModelFormat {
	/*
	* Input: int make - car make
	* Return: int[] format - [make, options]
	* Description: Uses m to define pid format
    * make exhibits the following behavior
    *               1: ThisAuto
    *               2: ThatAuto
    *               3: OtherAuto
    */

    public String[] pidFormat(String pidNumber);
}
