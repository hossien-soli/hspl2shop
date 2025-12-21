package dev.hspl.hspl2shop.user.component;

import dev.hspl.hspl2shop.user.model.impl.jpa.repository.VerificationSessionJpaRepository;
import lombok.RequiredArgsConstructor;

// removes old verification session records from sql database(clean up)!!
// when we store verification sessions on a database like redis that has ttl(time to live) for data...
// ...we don't need something like this

//@Component
@RequiredArgsConstructor
public class SqlVerificationSessionCleaner {
    private final VerificationSessionJpaRepository jpaRepository;


}
