import type { Actions, PageServerLoad } from './$types';
import { redirect } from '@sveltejs/kit';

export const load: PageServerLoad = ({ cookies, locals }) => {
  throw redirect(303, '/');
};

export const actions: Actions = {
  default: ({ cookies, locals }) => {
    cookies.delete('session');
    locals.user = null;

    throw redirect(303, '/');
  },
};
