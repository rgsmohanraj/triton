package org.vcpl.triton.maker.service;

import org.vcpl.triton.maker.model.Anchor;

interface IMaker {

   Anchor get(String anchorName);
   boolean save(String anchorName);
}
