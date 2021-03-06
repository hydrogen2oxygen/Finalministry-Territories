package net.hydrogen2oxygen.finalministry;

import net.hydrogen2oxygen.finalministry.jpa.Minister;
import net.hydrogen2oxygen.finalministry.jpa.Territory;
import net.hydrogen2oxygen.finalministry.jpa.TerritoryEntry;
import net.hydrogen2oxygen.finalministry.jpa.repositories.MinisterRepository;
import net.hydrogen2oxygen.finalministry.jpa.repositories.TerritoryEntryRepository;
import net.hydrogen2oxygen.finalministry.jpa.repositories.TerritoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Essential tests
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SmokeTest {

    @Autowired
    private MinisterRepository ministerRepository;

    @Autowired
    private TerritoryRepository territoryRepository;

    @Autowired
    private TerritoryEntryRepository territoryEntryRepository;

    @Before
    public void setup() {
        // The most important precondition is that the injected repository is not null
        Assert.assertNotNull(ministerRepository);
        Assert.assertNotNull(territoryRepository);
    }

    /**
     * Test create, read, update, delete
     */
    @Test
    public void test() {
        Minister minister = createMinister();
        Territory territory = createTerritory();

        TerritoryEntry territoryEntry = TerritoryEntry.generateForCongregationPool(territory);
        territoryEntry = territoryEntryRepository.save(territoryEntry);

        Assert.assertNotNull(territoryEntry.getId());
        Assert.assertTrue(territoryEntry.getCongregationPool());
        Assert.assertTrue(!territoryEntry.getArchived());
        Assert.assertEquals(territory.getId(), territoryEntry.getTerritoryID());
        Assert.assertNull(territoryEntry.getMinisterID());

        territoryEntry = TerritoryEntry.generateForMinister(territory, minister);
        territoryEntry = territoryEntryRepository.save(territoryEntry);

        Assert.assertNotNull(territoryEntry.getId());
        Assert.assertTrue(!territoryEntry.getCongregationPool());
        Assert.assertTrue(!territoryEntry.getArchived());
        Assert.assertEquals(territory.getId(), territoryEntry.getTerritoryID());
        Assert.assertEquals(minister.getId(), territoryEntry.getMinisterID());


        deleteMinister(minister);
        deleteTerritory(territory);
    }

    private Minister createMinister() {
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

        // FIND BY NAME
        Minister result = ministerRepository.findByName(minister.getName());
        Assert.assertNotNull(result);
        Assert.assertEquals(persistedMinister.getId(), result.getId());
        Assert.assertEquals(persistedMinister.getName(), result.getName());

        // UPDATE
        result.setName("John");
        ministerRepository.save(result);

        // FIND AGAIN ONE AND CHECK IF STILL SAME ID
        result = ministerRepository.findByName("John");
        Assert.assertNotNull(result.getName());
        Assert.assertEquals(persistedMinister.getId(), result.getId());
        return result;
    }

    private void deleteMinister(Minister result) {
        List<Minister> ministers;// DELETE
        ministerRepository.delete(result);

        // CHECK IF EMPTY
        ministers = ministerRepository.findAll();
        Assert.assertNotNull(ministers);
        Assert.assertEquals(0, ministers.size());
    }

    private Territory createTerritory() {
        // READ ALL
        List<Territory> territories = territoryRepository.findAll();
        Assert.assertNotNull(territories);
        Assert.assertEquals(0, territories.size());

        // CREATE ONE
        Territory territory = new Territory();
        territory.setNumber("100");
        territory.setName("Nürtingen Nord");

        Territory persisted = territoryRepository.save(territory);
        Assert.assertNotNull(persisted);
        Assert.assertNotNull(persisted.getId());

        // FIND ALL
        territories = territoryRepository.findAll();
        Assert.assertNotNull(territories);
        Assert.assertEquals(1, territories.size());
        Assert.assertEquals(persisted.getId(), territories.get(0).getId());

        // FIND BY NUMBER OR NAME
        Territory result = territoryRepository.findByName(territory.getName());
        Assert.assertNotNull(result);
        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());

        // UPDATE
        result.setName("Nürtingen Süd");
        territoryRepository.save(result);

        // FIND AGAIN ONE AND CHECK IF STILL SAME ID
        result = territoryRepository.findByName("Nürtingen Süd");
        Assert.assertNotNull(result.getName());
        Assert.assertEquals(persisted.getId(), result.getId());
        return result;
    }

    private void deleteTerritory(Territory result) {
        List<Territory> territories;// DELETE
        territoryRepository.delete(result);

        // CHECK IF EMPTY
        territories = territoryRepository.findAll();
        Assert.assertNotNull(territories);
        Assert.assertEquals(0, territories.size());
    }
}
