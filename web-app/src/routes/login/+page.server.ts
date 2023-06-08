import type { Actions, PageServerLoad } from './$types';

export const actions: Actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData();
    const psnId = data.get('psn_id');
    const password = data.get('password');

    // TODO: authenticate
    cookies.set('session', psnId as string);

    return {
      success: true,
    };
  },
};
