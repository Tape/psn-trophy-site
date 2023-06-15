import type { PageServerLoad } from './$types';
import { redirect } from '@sveltejs/kit';

export const load: PageServerLoad = ({ cookies }) => {
  cookies.delete('session');
  throw redirect(303, '/');
};
