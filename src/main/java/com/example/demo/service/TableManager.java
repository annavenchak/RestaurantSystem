package com.example.demo.service;

import com.example.demo.domain.Table;
import com.example.demo.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableManager implements CRUDOperations<Table> {

    private final TableRepository tableRepository;

    @Autowired
    public TableManager(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void create(Table t) {
        tableRepository.save(t);
    }

    @Override
    public List<Table> read() {
        return tableRepository.findAll();
    }

    public Table readById(int id) {
        return tableRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(int id, Table newItem) {
        if (tableRepository.existsById(id)) {
            newItem.setId(id);
            tableRepository.save(newItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (tableRepository.existsById(id)) {
            tableRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public int getCapacityOfTable(int id) {
        Table t = readById(id);
        return (t != null) ? t.getCapacity() : -1;
    }

    public int getTablesSize() {
        return (int) tableRepository.count();
    }
}