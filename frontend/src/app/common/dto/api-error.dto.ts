export type ApiErrorDTO = {
  timestamp: number;
  path: string;
  status: number;
  error: string;
  message: string;
  errors: ApiObjErr[];
  firstError?: ApiObjErr;
};

export type ApiObjErr = {
  defaultMessage: string;
  code: string;
};
