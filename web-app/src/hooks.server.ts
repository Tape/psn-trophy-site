import type { Handle } from '@sveltejs/kit';

export const handle: Handle = async ({ event, resolve }) => {
  const session = event.cookies.get('session');
  if (session) {
    event.locals.user = {
      id: session,
    };
  }

  return resolve(event);
};
