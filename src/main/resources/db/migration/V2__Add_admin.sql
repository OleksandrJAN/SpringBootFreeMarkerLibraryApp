insert into usr (id, username, password, active)
  values (1, 'admin', '$2a$08$eLovvQTHtjF8KchFojNvReAGD/Hc4ptxlZ4Q14bGyM5kFr7jLreNa', true);

insert into user_role (user_id, roles)
  values (1, 'USER'), (1, "ADMIN");