package movies.spring.data.neo4j;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin("*")
public class PersonnelController {

    private final Driver driver;

    public PersonnelController(Driver driver) {
        this.driver = driver;
    }

    @GetMapping("/personnel")
    public ResponseEntity<?> getPersonnelNodes() {
        try (Session session = driver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.READ).build())) {
            Object result = session.run("MATCH (n:personnel) RETURN n ORDER BY n.prenom ASC")
                    .list(r -> {
                                Personnel perso = new Personnel();
                        perso.setNom(r.get("n").asNode().get("nom").asString());
                        perso.setPrenom(r.get("n").asNode().get("prenom").asString());
                        perso.setFonction(r.get("n").asNode().get("fonction").asString());
                        perso.setVille(r.get("n").asNode().get("ville").asString());
                                return perso;
                            }
                    );
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Getting error");
        }
    }

    @PostMapping("/personnel/add")
    public ResponseEntity<?> createPersonnel(@RequestBody Personnel perso) {
        try (Session session = driver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            session.run(
                    "CREATE (n:personnel { " +
                            "nom:\"" + perso.getNom() +
                            "\",prenom:\"" + perso.getPrenom() +
                            "\",fonction:\"" + perso.getFonction() +
                            "\",ville:\"" + perso.getVille() +
                            "\"}) RETURN n "
            );
            return ResponseEntity.status(HttpStatus.OK).body(perso);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Creating error");
        }
    }

    @PutMapping("personnel/update/{nom}")
    public ResponseEntity<?> updatePersonnel(@RequestBody Personnel perso, @PathVariable("nom") String nom) {
        try (Session session = driver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            session.run(
                    "MATCH (n:personnel {nom:\"" +
                            nom + "\"}) SET " +
                            "n.nom=\"" + perso.getNom() +
                            "\", n.prenom=\"" + perso.getPrenom() +
                            "\", n.fonction=\"" + perso.getFonction() +
                            "\", n.ville=\"" + perso.getVille() +
                            "\" RETURN n "
            );
            return ResponseEntity.status(HttpStatus.OK).body(perso);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Updating error");
        }
    }

    @DeleteMapping("/personnel/delete/{nom}")
    public ResponseEntity<?> deletePerson(@PathVariable("nom") String nom) {
        try (Session session = driver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            session.run("MATCH (n:personnel { nom:\"" + nom +"\"}) DETACH DELETE n ");
            return ResponseEntity.status(HttpStatus.OK).body(nom);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deleting error");
        }
    }


    }
