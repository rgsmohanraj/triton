package org.vcpl.triton.maker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.maker.model.Anchor;
import org.vcpl.triton.maker.repository.IAnchorRepository;

@Service
public class Maker implements IMaker{

    @Autowired
    private IAnchorRepository anchorRepository;


    @Override
    public Anchor get(String name) {
        return anchorRepository.findByName(name);
    }

    @Override
    public boolean save(String  anchorName) {
        Anchor anchor = new Anchor();
        anchor.setName(anchorName);
        anchorRepository.save(anchor);
        return true;
    }
}
