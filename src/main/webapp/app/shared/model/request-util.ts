import { HttpParams } from '@angular/common/http';

export const createRequestOption = (req?: any): HttpParams => {
    let options: HttpParams = new HttpParams();
    if (req) {
        Object.keys(req).forEach((key) => {
            if (key !== 'sort') {
               if (key !== 'criteria') {
                    options = options.set(key, req[key]);
                }
            }
        });
        if (req.sort) {
            req.sort.forEach((val) => {
                options = options.append('sort', val);
            });
        }
        if (req.criteria && req.criteria.length > 0) {
            req.criteria.forEach((criterion) => {
                options = options.append(criterion.key, criterion.value);
            });
        }
    }
    return options;

};
