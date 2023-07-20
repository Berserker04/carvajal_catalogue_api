package com.carvajal.wishlist;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishList {
    private Id id;
    private Id userId;
    private Id productId;
    private State state;

    public WishList(Id id, Id userId, Id productId, State state) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.state = state;
    }
}
