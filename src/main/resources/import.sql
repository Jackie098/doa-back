-- Seed SQL executado no bootstrap do Hibernate (dev/test).
-- Mantemos inserts idempotentes para nao duplicar registros em cenarios de reexecucao.

INSERT INTO users (
  email,
  password,
  name,
  avatar_url,
  phone_number,
  type,
  is_active,
  first_access,
  is_charity_agent_member
)
SELECT
  'admin.seed@doa.local',
  '$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q',
  'Admin Seed',
  NULL,
  '85999990001',
  'ADM',
  true,
  false,
  false
WHERE NOT EXISTS (
  SELECT 1 FROM users WHERE email = 'admin.seed@doa.local'
);

INSERT INTO users (
  email,
  password,
  name,
  avatar_url,
  phone_number,
  type,
  is_active,
  first_access,
  is_charity_agent_member
)
SELECT
  'agente.seed@doa.local',
  '$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q',
  'Agente Seed',
  NULL,
  '85999990002',
  'CHARITY_AGENT',
  true,
  true,
  true
WHERE NOT EXISTS (
  SELECT 1 FROM users WHERE email = 'agente.seed@doa.local'
);

INSERT INTO users (
  email,
  password,
  name,
  avatar_url,
  phone_number,
  type,
  is_active,
  first_access,
  is_charity_agent_member
)
SELECT
  'voluntario.seed@doa.local',
  '$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q',
  'Voluntario Seed',
  NULL,
  '85999990003',
  'VOLUNTEER',
  true,
  true,
  false
WHERE NOT EXISTS (
  SELECT 1 FROM users WHERE email = 'voluntario.seed@doa.local'
);