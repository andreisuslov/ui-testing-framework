package utilities;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class MockData {

    private  FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-US"), new RandomService());


    public String generateRandomEmail(){
        String email = fakeValuesService.bothify(new Faker().name().firstName() +"####@testemail.com");
        return email;
    }

     public String generateRandomSsn(){
        String ssn = String.format("%09d", new Random().nextInt(1000000000));
        return ssn;
    }
}
