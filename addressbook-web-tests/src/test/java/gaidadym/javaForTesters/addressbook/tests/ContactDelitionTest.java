package gaidadym.javaForTesters.addressbook.tests;

import gaidadym.javaForTesters.addressbook.TestBase;
import gaidadym.javaForTesters.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactDelitionTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition(){
        if (app.contact().list().size()==0){
            app.contact().create((new ContactData().withFirstname("test1")
                    .withLastname("Testovich").withMiddlename("Testoviy")));
            app.goTo().mainPage();
        }
    }

    @Test

    public void testContactDelition() throws Exception {

        Set<ContactData> before = app.contact().all();
        int index = before.size()-1;
        app.contact().delete(index);
        Set<ContactData> after = app.contact().all();
        before.remove(before.size()-1);
        Assert.assertEquals(new HashSet<Object>(after),new HashSet<Object>(before));

    }

}
