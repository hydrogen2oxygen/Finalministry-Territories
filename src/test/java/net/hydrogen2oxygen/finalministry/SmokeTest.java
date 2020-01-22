package net.hydrogen2oxygen.finalministry;

import net.hydrogen2oxygen.finalministry.jpa.Minister;
import net.hydrogen2oxygen.finalministry.jpa.repositories.MinisterRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Essential tests
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SmokeTest {

    @Autowired
    private MinisterRepository ministerRepository;

    @Before
    public void setup() {
        // The most important precondition is that the injected repository is not null
        Assert.assertNotNull(ministerRepository);
    }

    /**
     * Test create, read, update, delete
     */
    @Test
    public void testPostRepositoryCRUD() {
        // READ ALL
        List<Minister> ministers = ministerRepository.findAll();
        Assert.assertNotNull(ministers);
        Assert.assertEquals(0, ministers.size());

        // CREATE ONE
        Minister minister = new Minister();
        minister.setName("Jack");

        Minister persistedMinister = ministerRepository.save(minister);
        Assert.assertNotNull(persistedMinister);
        Assert.assertNotNull(persistedMinister.getId());

        // FIND ALL
        ministers = ministerRepository.findAll();
        Assert.assertNotNull(ministers);
        Assert.assertEquals(1, ministers.size());
        Assert.assertEquals(persistedMinister.getId(), ministers.get(0).getId());

        // FIND BY TITLE
        Minister result = ministerRepository.findByName(minister.getName());
        Assert.assertNotNull(result);
        Assert.assertEquals(persistedMinister.getId(), result.getId());
        Assert.assertEquals(persistedMinister.getName(), result.getName());

        // UPDATE
        result.setName("John");
        ministerRepository.save(result);

        // FIND AGAIN ONE AND CHECK IF STILL SAME ID
        result = ministerRepository.findByName("John");
        System.out.println(result);
        Assert.assertNotNull(result.getName());
        Assert.assertEquals(persistedMinister.getId(), result.getId());

        // DELETE
        ministerRepository.delete(result);

        // CHECK IF EMPTY
        ministers = ministerRepository.findAll();
        Assert.assertNotNull(ministers);
        Assert.assertEquals(0, ministers.size());
    }
}
