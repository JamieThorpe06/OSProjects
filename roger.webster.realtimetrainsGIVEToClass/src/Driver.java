
public class Driver {

	public static void main(String[] args) {
		FAArray fa = new FAArray();
		
		System.out.println(fa.getCurrent().getName());
		
		int i = 0;
		while(i < 7){
			fa.next();
			System.out.println(fa.getCurrent().getName());
			i ++;
		}
		
		int j = 0;
		while (j < 16){
			if(fa.getCurrent().hasJump()){
				fa.jump();
			}
			else{
				fa.next();
			}
			System.out.println(fa.getCurrent().getName());
			j ++;
		}

	}

}
