# PSN Trophy Site (OpenPSN)

## Proposal

The purpose of this project is to provide a way for the PSN community to contribute to an open source site for trophy
tracking and guides. This project will never be monetized outside efforts to keep the lights on in terms of
infrastructure costs.

## Planned Features

- Add PSN account
- Game list for user
- Trophies for each title
- Browse other users
  - Mindful of user's game preferences
- Search for games
  - Aggregate into single game showing all platforms?
- Trophy guides for games

## Technology Choices

### Backend

- Jooby
  - High throughput
  - Asynchronous by design
  - Easy to pick up quickly
  - Modular
- PostgreSQL
  - Great relational capabilities
  - Great NoSQL capabilities
- Redis
  - Fast cache
  - Session management

### Backend for Frontend

- SvelteKit
  - TypeScript 
  - Server Side Rendering

### Frontend

- SvelteKit
  - TypeScript
  - Single file components
  - Scoped CSS
  - Modular
- Vite
  - Fast
  - Simple
  - Modular
