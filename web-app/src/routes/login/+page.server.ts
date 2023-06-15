import type { Actions } from './$types';

export const actions: Actions = {
  default: async ({ cookies, locals, request }) => {
    const data = await request.formData();
    const psnId = data.get('psn_id') as string;
    const password = data.get('password') as string;

    // TODO: authenticate
    cookies.set('session', psnId as string);
    locals.user = {
      id: psnId,
    };

    return {
      success: true,
    };
  },
};
