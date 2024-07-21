package com.example.hackathon.Controller;

import com.example.hackathon.Entity.Transaction;
import com.example.hackathon.Service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransaction();
    }

    @GetMapping("/getTransactionByID/{transaction_id}")
    public Transaction getTransactionByID(@PathVariable Long transaction_id){
        return transactionService.getTransactionByID(transaction_id);
    }

    @PutMapping("/update-transaction/{transaction_id}")
    public void updateTransation(@RequestBody Transaction updated_transaction, @PathVariable Long transaction_id){
        transactionService.updateTransaction(updated_transaction,transaction_id);
    }

    @PostMapping("/create-transaction")
    public void createTransaction(@RequestBody Transaction new_transaction){
        transactionService.createTransaction(new_transaction);

    }

    @DeleteMapping("/delete-transaction/{transaction_id}")
    public void deleteTransation(@PathVariable Long transaction_id){

        transactionService.deleteTransaction(transaction_id);

    }
}
