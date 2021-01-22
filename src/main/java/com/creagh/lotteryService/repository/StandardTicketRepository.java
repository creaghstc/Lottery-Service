package com.creagh.lotteryService.repository;

import com.creagh.lotteryService.entity.StandardTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class StandardTicketRepository implements TicketRepository {

    @Autowired
    EntityManagerFactory emf;
    EntityManager entityManager;

    /**
     * Method to save a new standard ticket entity
     *
     * @param ticketRequestDto
     * @return
     */
    @Override
    @Transactional
    public StandardTicket saveTicket(StandardTicket ticketRequestDto) {
        entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(ticketRequestDto);
        entityManager.getTransaction().commit();
        entityManager.close();

        return ticketRequestDto;
    }

    /**
     * Method to update a given Standard ticket entity.
     *
     * @param standardTicket
     * @return
     */
    @Override
    public StandardTicket updateTicket(StandardTicket standardTicket) {
        entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.merge(standardTicket);
        entityManager.getTransaction().commit();
        entityManager.close();

        return standardTicket;
    }

    /**
     * Method to find all tickets.
     *
     * @return
     */
    @Override
    public List<StandardTicket> findAllTickets() {

        entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        List<StandardTicket> result = entityManager.createQuery("SELECT a FROM StandardTicket a").getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return result;
    }

    /**
     * Methoid to find a specific ticket by id.
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public StandardTicket findTicketById(int id) {
        entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        StandardTicket standardTicket = entityManager.find(StandardTicket.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return standardTicket;
    }


}
