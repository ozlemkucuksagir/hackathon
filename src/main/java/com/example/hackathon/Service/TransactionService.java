package com.example.hackathon.Service;

import com.example.hackathon.Entity.Transaction;
import com.example.hackathon.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransaction(){
        return transactionRepository.findAll();
    }

    public Transaction getTransactionByID(Long transaction_id){
        return transactionRepository.findById(transaction_id).get();

    }

    public void deleteTransaction(Long transaction_id){
        transactionRepository.deleteById(transaction_id);
    }

    public void createTransaction(Transaction new_transaction){
        transactionRepository.save(new_transaction);
    }

    public void updateTransaction(Transaction updated_transaction,Long transaction_id){
        Transaction transaction;

        transaction=transactionRepository.findById(transaction_id).get();
        transaction.setAmount(updated_transaction.getAmount());
        transaction.setDate(updated_transaction.getDate());
        transaction.setDescription(updated_transaction.getDescription());
        transaction.setUser(updated_transaction.getUser());

        transactionRepository.save(transaction);


    }

}
