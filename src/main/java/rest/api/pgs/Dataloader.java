package rest.api.pgs;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class Dataloader {

    public static void main(String[] args) {


            for(int i=1;i<25;i++){

                Faker faker = new Faker(new Locale("pl_PL"));

                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email=firstName.toLowerCase()+"."+lastName.toLowerCase()+"@gmail.com";
                email = StringUtils.stripAccents(email); // lack of polish special characters

                String output ;
                output=String.format("insert into pgs.users values (%d,false,'%s','%s','%s','$$2a$10$v2jHZ/IdBq2/twWzqzQe/emY.urleja7TEdiZENy20TNCkdSftDB6')",i+1,email,firstName,lastName);
                System.out.println(output);

                String output2= String.format("insert into user_role values (%d,2)",i+1);


//                System.out.println(output2);



            }

    }
}
