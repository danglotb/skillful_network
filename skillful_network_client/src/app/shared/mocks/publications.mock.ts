import { User } from 'src/app/shared/models/user/user';
import { PublicationComment } from 'src/app/shared/models/publication-comment';
import { MOCK_COMMENTS, IComment } from './comments.mock';
export interface IPublication {
    id: number;
    user: string;
    text: string;
    file: string;
    numberOfComment: number;
    comments: IComment[],
    votes: number;
    dateOfPost: Date;
}

export let MOCK_PUBLICATIONS: IPublication[] = [
    {
        id: 1,
        user: "Capgimini",
        text: 'Recrutement : Notre agence de Sophia Antipolis recrute un dévéloppeur web full stack',
        file: "https://cap.img.pmdstatic.net/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2Fcap.2F2018.2F09.2F21.2F266bfa2e-0033-4111-a124-a93a5dd20ca5.2Ejpeg/750x375/background-color/ffffff/focus-point/1093%2C1211/quality/70/deux-salaries-sur-trois-jugent-leur-travail-nerveusement-fatigant-1307881.jpg",
        votes: 3,
        numberOfComment: 1,
        comments:[{
            id: 1,
            user: "Huet Mathilde",
            text:"C'est une publication trés intéressante",
            votes: 3,
            dateOfComment: new Date("2020-06-16") ,
        }],                    
        dateOfPost: new Date("2020-06-16") ,
    },
    {
        id: 2,
        user: "Softeam Institute",
        text: 'La nouvelle session de formation de Java/J2E se tiendra au mois de Septembre',
        file: 'https://www.fedhuman.fr/system/redactor2_assets/images/14/content_fiche-metier-responsable-de-formation-ou-chef-de-projet-formation-0.jpg',
        votes: 5,
        numberOfComment: 1,
        comments: [MOCK_COMMENTS[1]],
        dateOfPost: new Date("2020-07-01") ,
    },
    {
        id: 3,
        user: "Dupont Maeva",
        text: "Je m'approche à grand pas de ma sortie du monde étudiant et je suis en pleine recherche d'une opportunité dans le domaine de l'informatique",
        file: 'https://www.mypacs.fr/images/avantages-travail-pacs-conge.jpg',
        votes:6,
        numberOfComment: 1,
        comments: [MOCK_COMMENTS[2]],
        dateOfPost: new Date("2020-06-11") ,
    },
    {
        id: 4,
        user: "André Yanis",
        text: "Etudiant en 5éme année école d'ingénieur je cherche une alternance à partir du Septembre 2020",
        file: 'https://cap.img.pmdstatic.net/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2Fcap.2F2019.2F06.2F20.2F21b85bb6-a115-4415-bb06-d15d4db72b91.2Ejpeg/750x375/background-color/ffffff/quality/70/ca-veut-dire-quoi-le-bien-etre-au-travail-1342496.jpg',
        votes:6,
        numberOfComment: 1,
        comments: [MOCK_COMMENTS[2]],
        dateOfPost: new Date("2020-06-11") ,
    },
];