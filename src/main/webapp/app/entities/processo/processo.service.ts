import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Processo } from './processo.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Processo>;

@Injectable()
export class ProcessoService {

    private resourceUrl =  SERVER_API_URL + 'api/processos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(processo: Processo): Observable<EntityResponseType> {
        const copy = this.convert(processo);
        return this.http.post<Processo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(processo: Processo): Observable<EntityResponseType> {
        const copy = this.convert(processo);
        return this.http.put<Processo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Processo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Processo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Processo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Processo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Processo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Processo[]>): HttpResponse<Processo[]> {
        const jsonResponse: Processo[] = res.body;
        const body: Processo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Processo.
     */
    private convertItemFromServer(processo: Processo): Processo {
        const copy: Processo = Object.assign({}, processo);
        copy.dataCadastro = this.dateUtils
            .convertDateTimeFromServer(processo.dataCadastro);
        copy.dataAtualizacao = this.dateUtils
            .convertDateTimeFromServer(processo.dataAtualizacao);
        return copy;
    }

    /**
     * Convert a Processo to a JSON which can be sent to the server.
     */
    private convert(processo: Processo): Processo {
        const copy: Processo = Object.assign({}, processo);

        copy.dataCadastro = this.dateUtils.toDate(processo.dataCadastro);

        copy.dataAtualizacao = this.dateUtils.toDate(processo.dataAtualizacao);
        return copy;
    }
}
