import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Parecer } from './parecer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Parecer>;

@Injectable()
export class ParecerService {

    private resourceUrl =  SERVER_API_URL + 'api/parecers';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(parecer: Parecer): Observable<EntityResponseType> {
        const copy = this.convert(parecer);
        return this.http.post<Parecer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(parecer: Parecer): Observable<EntityResponseType> {
        const copy = this.convert(parecer);
        return this.http.put<Parecer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Parecer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Parecer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Parecer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Parecer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Parecer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Parecer[]>): HttpResponse<Parecer[]> {
        const jsonResponse: Parecer[] = res.body;
        const body: Parecer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Parecer.
     */
    private convertItemFromServer(parecer: Parecer): Parecer {
        const copy: Parecer = Object.assign({}, parecer);
        copy.dataCadastro = this.dateUtils
            .convertDateTimeFromServer(parecer.dataCadastro);
        copy.dataAtualizacao = this.dateUtils
            .convertDateTimeFromServer(parecer.dataAtualizacao);
        return copy;
    }

    /**
     * Convert a Parecer to a JSON which can be sent to the server.
     */
    private convert(parecer: Parecer): Parecer {
        const copy: Parecer = Object.assign({}, parecer);

        copy.dataCadastro = this.dateUtils.toDate(parecer.dataCadastro);

        copy.dataAtualizacao = this.dateUtils.toDate(parecer.dataAtualizacao);
        return copy;
    }
}
